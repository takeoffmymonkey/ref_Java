package types_references_annotations.my_annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // аннотации будут видны в рантайме
@Repeatable(Ntrstns.class)
public @interface Ntrstn {
    String value() default "";
}