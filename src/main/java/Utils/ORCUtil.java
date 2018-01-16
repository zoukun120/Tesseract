package Utils;

import org.jdesktop.swingx.util.OS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ORCUtil {

    private final static String LANG_OPTION="-l";

    private final static String EOL=System.getProperty("line.separator");//line.separator 行分隔符

    private final static String tesseractPath=new File("E:\\SoftWare_List_D\\Tesseract-OCR").getAbsolutePath();//tesseract文件放在项目文件里了

    /**
     *此方法功能：识别图片中的文字并返回到指定txt文件中
     * @param image 输入一张图片（这里放在了项目目录）
     */
    public static String recognizeText(File image) throws Exception{

        File outputfile = new File(image.getParentFile(), "output");//输出文件的保存目录

        StringBuffer strB  = new StringBuffer();
        List<String> cmd = new ArrayList<String>();//数组各个位置存放东西，如[tesseract.exe的路径，识别的图像，输出文件名，命令选项-l，语言选择]

        if (OS.isLinux()){  //OS需要导入SwingX的jar包
            cmd.add("tesseract");
        }
        else {
            cmd.add(tesseractPath+"\\tesseract");
        }
        cmd.add("");
        cmd.add(outputfile.getName());
        cmd.add(LANG_OPTION);
        cmd.add("chi_sim");
//        cmd.add("eng");
        cmd.set(1, image.getName());//把cmd数组中的第二个位置放置图片
        System.out.println("cmd数组:"+cmd);

        ProcessBuilder pb = new ProcessBuilder();//创建有个进程生成器实例  深入研究 http://blog.51cto.com/lavasoft/15662
        pb.directory(image.getParentFile());//设置此进程生成器的工作目录
        System.out.println("\n识别的图片名为："+image.getName());
        pb.command(cmd);// 设置此进程生成器的操作系统程序和参数
        pb.redirectErrorStream(true);
//      前面都是在为组装命令做准备:tesseract.exe 1.jpg 1 -l chi_sim,然后执行命令：Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        Process process = pb.start();

        System.out.println(cmd.toString());//E:\SoftWare_List_D\Tesseract-OCR\下执行命令[tesseract 2.jpg output -l chi_sim]

        int w = process.waitFor();
        System.out.println("w的值："+w);
        if (w==0){
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputfile.getAbsolutePath()+".txt"),"utf-8"));
            String str;
            while ((str=in.readLine())!=null){
                strB.append(str).append(EOL);
            }
            in.close();
        }
        else{
            String msg;
            switch (w){
                case 1:msg="Errors accessing files. There may be spaces in your image's filename.";break;
                case 29:msg="Cannot recognize the image or its selected region.";break;
                case 31:msg="Unsupported image format.";break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputfile.getAbsolutePath() + ".txt").delete();
        return strB.toString().replaceAll("\\s*","");
    }
}
