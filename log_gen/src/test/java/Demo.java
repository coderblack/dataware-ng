public class Demo {
    public static void main(String[] args) {
        new Ac();

        new A(){
            @Override
            public void say2() {

            }
        };

        class Ad extends A{

            @Override
            public void say2() {

            }
        }

    }
}
