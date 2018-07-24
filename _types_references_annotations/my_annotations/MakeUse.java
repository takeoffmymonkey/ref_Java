package _types_references_annotations.my_annotations;

import java.lang.annotation.Repeatable;

@Repeatable(MakeUses.class)
public @interface MakeUse {
    String value() default "";
}