package types_references_annotations.my_annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // аннотации будут видны в рантайме
public @interface Ntrstns {
    Ntrstn[] value();
}