package lang_identifiers;

/* Все компоненты Java требуют имена */

public class Main {
    public static void main(String[] args) {
        /*ПОСЛЕДОВАТЕЛЬНОСТЬ JAVA БУКВ И ЦИФР*/
        Character.isJavaIdentifierStart('d'); // для проверки букв
        Character.isJavaIdentifierPart(12); // для проверки  цифр


        /*ЧУВСТВИТЕЛЕН К РЕГИСТРУ*/
        int name;
        int Name;
        int NAME;


        /*ДОЛЖЕН НАЧИНАТЬСЯ С БУКВЫ, «$» ИЛИ «_»*/
        int A; // заглавные
        int a; // строчные
        int _b; // нижнее подчеркивание
        int $df; // не рекомендуется, т.к. служит для обозначения имен, формируемых javac и другими
        // средствами


        /* МОЖЕТ БЫТЬ ЗАПИСАН СИМВОЛАМИ Unicode, Т.Е. ЛЮБЫМ ЯЗЫКОМ*/
        int змinna;
        int αρετη;


        /* НЕ МОЖЕТ НАЧИНАТЬСЯ С ЦИФРЫ*/
        //int 1wrong = 12;


        /* НЕ МОЖЕТ СОВПАДАТЬ СО СПЕЦИАЛЬНЫМИ СЛОВАМИ В JAVA*/
        // int switch; // не может быть ключевым словом
        // int true; // не может быть булеановым литералом
        // int null; // не может быть литералом null;


        /*ПОСЛЕ ПЕРВОГО СИМВОЛА МОЖЕТ ИМЕТЬ ЛЮБУЮ КОМБИНАЦИЮ СИМВОЛОВ*/
        int s12null;


        /* НЕ МОЖЕТ БЫТЬ С ПРОБЕЛОМ*/
        // int wrong name;


        /* НЕТ ОГРАНИЧЕНИЯ ДЛИНЫ (хотя вроде 2^32)*/
        int dsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjgdsfvsfsgoTS4etsvdalkvmfdbvsdnmsdfsdsfsdfjksldfsdlfjsdlkgjslkgdsfhjghsdfgsldfhgsldfjkghsldkfjg;


        /* СИМВОЛЫ РАВНЫ ТОЛЬКО, ЕСЛИ ОНИ РАВНЫ В UNICODE*/
        int c; // латинская с
        int с; // не равна русской с
    }
}
