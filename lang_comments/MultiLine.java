package lang_comments;

public class MultiLine {

/* это
многострочный
комментарий
*/

    /*НЕ МОЖЕТ БЫТЬ ВЛОЖЕННЫМ (НЕЛЬЗЯ ЗАКОММЕНТИРОВАТЬ БЛОК КОДА С МНОГОСТРОЧНЫМ КОММЕНТАРИЕМ)
     * (но IDE поставит нужные символы начала и конца комментариев, так что все будет ок)*/ {
        int i = 7;
        /*комментарий внутри*/
        int a = 4;
    }

    // УПРАВЛЯЮЩИЕ ПОСЛЕДОВАТЕЛЬНОСТИ НЕ ИГНОРИРУЮТСЯ В КОММЕНТАРИЯХ (ошибка компиляции!)
}
