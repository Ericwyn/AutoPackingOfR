import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ericwyn on 17-10-12.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("please input the full path about empty folder for package build :");
        Scanner scanner=new Scanner(System.in);
        String rootPath =scanner.nextLine();
        if (rootPath==null || rootPath.isEmpty() || rootPath.replaceAll(" ","").equals("")){
            System.out.println("ERROR : the full path about empty folder for package is WRONG");
            return;
        }
        System.out.println("please input a full path of R language file");
        String rFilePathTemp=scanner.nextLine();
        if (rFilePathTemp==null || rFilePathTemp.isEmpty() || rFilePathTemp.replaceAll(" ","").equals("")){
            System.out.println("ERROR : the full path about empty folder for package");
            return;
        }
        ArrayList<String > rFilePathList=new ArrayList<>();
        rFilePathList.add(rFilePathTemp);
        String inputTemp="";
        while (inputTemp!=null){
            System.out.println("please input a full path of R language file , or input the \"n\" to start build R package folder:");
            inputTemp=scanner.nextLine();
            if(inputTemp.equals("n") || inputTemp.equals("no") || inputTemp.equals("N") || inputTemp.equals("NO") || inputTemp.equals("nO") || inputTemp.equals("No")){
                inputTemp=null;
            }else {
                if(inputTemp.isEmpty() || inputTemp.replaceAll(" ","").equals("")){

                }else {
                    rFilePathList.add(inputTemp);
                }
            }
        }

        String packagePath = Model.createPackageFolder(rootPath,rFilePathList);

        if(packagePath !=null){
            Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
            try {
                Process p = run.exec("R CMD check "+packagePath);// 启动另一个进程来执行命令
                BufferedInputStream in = new BufferedInputStream(p.getInputStream());
                BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
                String lineStr;
                String outputTemp="";
                while ((lineStr = inBr.readLine()) != null){
                    outputTemp += lineStr+"\n";
                    System.out.println(lineStr);// 打印输出信息
                }
                System.out.println("\n===========  R CMD check finish  ============");
                //检查命令是否执行失败。
                if (p.waitFor() != 0) {
                    if(outputTemp.contains("* DONE") && outputTemp.contains("Status: ")){
                        System.out.println();
                        System.out.println("           check successful");
                        System.out.println();
                        System.out.println("       and now ,open the R CMD build");
                        System.out.println();
                        System.out.println("=============================================");
                        System.out.println();

                        run = Runtime.getRuntime();
                        p = run.exec("R CMD build "+packagePath);// 启动另一个进程来执行命令
                        in = new BufferedInputStream(p.getInputStream());
                        inBr = new BufferedReader(new InputStreamReader(in));
                        lineStr=null;
                        outputTemp="";
                        while ((lineStr = inBr.readLine()) != null){
                            outputTemp += lineStr+"\n";
                            System.out.println(lineStr);// 打印输出信息
                        }
                        if(outputTemp.contains("* building ‘")){
                            System.out.println("\n===========  R CMD build finish  ============");
                            System.out.println();
                            System.out.println("                build successful");
                            System.out.println();
                            System.out.println("output dir  : " +rootPath);
                            System.out.println();
                            System.out.println("=============================================");
                        }else {
                            System.out.println("\n===========  R CMD build finish  ============");
                            System.out.println();
                            System.out.println("                build failed");
                            System.out.println();
                            System.out.println("     please check the R package files.");
                            System.out.println();
                            System.out.println("=============================================");
                        }
                    }else {
                        System.out.println();
                        System.out.println("                check failed");
                        System.out.println();
                        System.out.println("     please check the R package files.");
                        System.out.println();
                        System.out.println("                 packing end ");
                        System.out.println();
                        System.out.println("=============================================");
                        System.out.println();
                    }
                }
                inBr.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {

        }
    }
}
//  /home/ericwyn/work/chance
//  testfun.R