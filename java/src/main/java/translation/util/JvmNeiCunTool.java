
package translation.util;

/**
* ClassName: JvmNeiCunTool <br/>
* @author
* @version  
* @since JDK 1.8
**/
public class JvmNeiCunTool {
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime(); 
		showRuntimeNeiCun(runtime);
	}
	public static void showRuntimeNeiCun(Runtime runtime) {
		System.out.println("\n");
		long free = Runtime.getRuntime().freeMemory();
		System.out.println("所分配内存的剩余大小(所分配中的空闲内存(freeMemory()))为:{"+free+"}byte;"+"{"+(free/1024)+"}"+"KB;"+"{"+(free/1024/1024)+"MB"+"}");
		// 的JVM内存总量（单位是字节）-已经废品到的内存
		long total = Runtime.getRuntime().totalMemory();
		System.out.println("JVM已经分配到的内存为(totalMemory())为:{"+total+"}byte;"+"{"+(total/1024)+"}"+"KB;"+"{"+(total/1024/1024)+"MB"+"}");
		// JVM试图使用额最大内存量（单位是字节）
		long max = Runtime.getRuntime().maxMemory();
		System.out.println("JVM可以使用的最大内存(maxMemory())为:{"+max+"}byte;"+"{"+(max/1024)+"}"+"KB;"+"{"+(max/1024/1024)+"MB"+"}");
        //JVM中已经使用的内存(单位字节)
		long used = total-free;
        System.out.println(String.format("JVM中已经使用的内存(totalMemory()-freeMemory())大小为:{%d}byte;{%d}KB;{%s}MB",used,used/1024,used/1024/1024));
        long usable = max-(total-free);
//        usable= max - total + free;
        System.out.println(String.format("JVM中最大可用内存(maxMemory-totalMemory+freeMemory)为:{%d}byte;{%d}KB;{%s}MB",usable,usable/1024,usable/1024/1024));
		System.out.println("\n");
	};


}