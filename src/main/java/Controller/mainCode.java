package Controller;

import Utils.ORCUtil;

import java.io.File;

public class mainCode {

    public static void main(String[] args) {
        try{
            File testDataDir = new File("E:\\SoftWare_List_D\\Tesseract-OCR\\tessdata");
            System.out.println("tessdata目录下共有 "+testDataDir.listFiles().length+" 个文件/文件夹");//listFiles()方法是返回某个目录下所有文件和目录的绝对路径，返回的是File数组
            //这里我直接指定了一个文件进行识别（图像质量越好，识别的准确度越高）
            File file = new File("E:\\SoftWare_List_D\\Tesseract-OCR\\tessdata\\willbeopreatedimages\\img3.png");
            String recognizeText = new ORCUtil().recognizeText(file);
            System.out.print(recognizeText+"\t");
//            int i = 0 ;
//            for(File file :testDataDir.listFiles()) {
//                i++ ;
//                String recognizeText = new ORCUtil().recognizeText(file);
//                System.out.print(recognizeText+"\t");
//
//                if( i % 5  == 0 )
//                {
//                    System.out.println();
//                }
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}



