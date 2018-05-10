package types_references_annotations.my_annotations;

import java.lang.annotation.Repeatable;

@Repeatable(Ntrstns.class)
public @interface Ntrstn {
    String value() default "";
}