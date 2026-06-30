package demo.strategy;

import org.springframework.stereotype.Component;

@Component("Base62URLTransStrategy")
public class Base62URLTransStrategy implements URLTransStrategy {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int base = allowedCharacters.length;

    @Override
    public String encode(Integer id) {
        if(id == 0) return String.valueOf(allowedCharacters[0]);

        int num = id;
        StringBuilder encodedString = new StringBuilder();
        while (num > 0) {
            encodedString.append(allowedCharacters[(int) (num % base)]);
            num = num / base;
        }

        return encodedString.reverse().toString();
    }

    // 暂未使用，保证覆盖率先注释掉
//    @Override
//    public Integer decode(String curURL) {
//        char[] characters = curURL.toCharArray();
//
//        int length = characters.length;
//        int id = 0;
//        int counter = 1;
//
//        for (char character : characters) {
//            id += allowedString.indexOf(character) * Math.pow(base, length - counter);
//            counter++;
//        }
//
//        return id;
//    }
}
