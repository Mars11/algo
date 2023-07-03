package cn.cqsztech.innerClass;

public class InnerClass {
    private int out;
    private class Inner{
        private int in;
        public int say(){
            System.out.println(out);
            return InnerClass.this.out;
        }
    }
    public  Inner st() {
        Inner inner = new Inner();
        return inner;
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.out=1;
        Inner st = innerClass.st();
        st.say();
    }
}
