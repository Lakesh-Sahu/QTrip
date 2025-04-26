package qtrip.utils;

public class Helper {
    public String emailDynamic(String s) {
        return System.currentTimeMillis() + s;
    }

    public int[] durationSeparator(String s) {
        int[] arr = new int[2]; 
        int i = 0;

        for (String str : s.split("-")) {
            StringBuilder sb = new StringBuilder();
            i++;
            for (Character c : str.toCharArray()) {
                
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if(i == 1) {
                arr[0] = Integer.parseInt(sb.toString());
            } else {
                arr[1] = Integer.parseInt(sb.toString());
            }
        }

        return arr;
    }
}
