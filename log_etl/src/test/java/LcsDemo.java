public class LcsDemo {
    public static void main(String[] args) {

        String[] x = {"","A","B","E","D"};
        String[] y = {"","A","B","C","A","B","X","E","D"};


        int[][] c  = new int[x.length][y.length];
        int[][] b = new int[x.length][y.length];
        for(int i=1;i<x.length;i++){
            for(int j=1;j<y.length;j++){

                if(x[i].equals(y[j])){

                    c[i][j] = c[i-1][j-1]+1;
                    b[i][j] = 0;

                }else if(c[i][j-1] >= c[i-1][j]){
                    c[i][j] = c[i][j-1];
                    b[i][j] = 1;

                }else{
                    c[i][j] = c[i-1][j];
                    b[i][j] = -1;
                }
            }
        }

        System.out.println(c[x.length-1][y.length-1]);

        for(int i =0;i<x.length;i++){
            for(int j=0;j<y.length;j++){
                System.out.print(c[i][j]+",");
            }
            System.out.println();
        }
        System.out.println();
        lcs(b,x,x.length-1,y.length-1);
    }

    public static void lcs(int[][] b,String[] x,int i,int j){
        if(i==0 || j==0){
            return ;
        }
        if(b[i][j] == 0){
            lcs(b,x,i-1,j-1);
            System.out.println(x[i]);
        }else if(b[i][j]==1){
            lcs(b,x,i,j-1);

        }else{
            lcs(b,x,i-1,j);
        }
    }

}
