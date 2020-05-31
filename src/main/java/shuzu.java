public class shuzu {
    public static void main(String[] args) {
        myclass my=new myclass();
        myclass[] list= new myclass[]{my,my,my};
        System.out.println("mytest");
        for(myclass i:list){
            System.out.println(i.getClass());
            //mytest
        }

    }
}
