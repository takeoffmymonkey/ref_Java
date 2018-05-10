package modifiers_access;

import modifiers_access.some_package.SomeClass;

/* public - доступ к члену везде (при условии импорта)
 * protected - доступ к члену только в том же в пакете + в наследниках
 * default- доступ к члену только в том же в пакете
 * private -  доступ к члену только в том же классе*/


/* ДОСТУП ОПРЕДЕЛЯЕТСЯ МЕСТОМ ОБРАЩЕНИЯ К ЧЛЕНУ, А НЕ К ЕГО ОБЪЕКТУ!
 * - т.е.
 *      - я могу обратится к private члену, если нахожусь в его классе - даже через объект данного
 *      класса
 *      - я не могу обратится к default члену даже через объект этого класса, если нахожусь вне его
 *      пакета
 *      - я могу обратится к члену protected, если нахожусь в самом классе, в его пакете или в его
 *      наследнике, но не могу обратится к члену через объект класса или наследника, если нахожусь
 *      не в его пакете! */
public class Main extends SomeClass {

    public static void main(String[] args) {
        System.out.println("\"Access Modifiers\"");


        /*Public*/
        PublicModifier publicModifier = new PublicModifier();
        publicModifier.publicField = 0;
        publicModifier.publicMethod();

        PublicModifier.PublicInnerClass publicInnerClass = publicModifier.new PublicInnerClass();
        publicInnerClass.publicInnerField = 0;
        publicInnerClass.publicInnerMethod();

        PublicModifier.PublicInnerStaticClass publicStaticInnerClass
                = new PublicModifier.PublicInnerStaticClass();
        publicStaticInnerClass.publicInnerStaticClassField = 0;
        publicStaticInnerClass.publicInnerStaticClassMethod();

        PublicModifier.PublicInnerInterface publicInnerInterface
                = new PublicModifier.PublicInnerInterface() {
            @Override
            public void alwaysPublicAbstractMethod() {
            }
        };

        PublicOuterInterface publicOuterInterface = new PublicOuterInterface() {
            @Override
            public void alwaysPublicAbstractMethod() {
            }
        };


        /*Protected*/
        ProtectedModifier protectedModifier = new ProtectedModifier();
        protectedModifier.protectedField = 0;
        protectedModifier.protectedMethod();

        ProtectedModifier.ProtectedInnerClass protectedInnerClass
                = protectedModifier.new ProtectedInnerClass();
        protectedInnerClass.protectedInnerField = 0;
        protectedInnerClass.protectedInnerMethod();

        ProtectedModifier.ProtectedInnerStaticClass protectedStaticInnerClass
                = new ProtectedModifier.ProtectedInnerStaticClass();
        protectedStaticInnerClass.protectedInnerStaticClassField = 0;
        protectedStaticInnerClass.protectedInnerStaticClassMethod();

        ProtectedModifier.ProtectedInnerInterface protectedInnerInterface
                = new ProtectedModifier.ProtectedInnerInterface() {
            @Override
            public void alwaysPublicAbstractMethod() {
            }
        };

        ProtectedOuterInterface protectedOuterInterface = new ProtectedOuterInterface() {
            @Override
            public void nonProtectedMethod() {
            }
        };


        /*Default*/
        DefaultModifier defaultModifier = new DefaultModifier();
        defaultModifier.defaultField = 0;
        defaultModifier.defaultMethod();

        DefaultModifier.DefaultInnerClass defaultInnerClass = defaultModifier.new DefaultInnerClass();
        defaultInnerClass.defaultInnerField = 0;
        defaultInnerClass.defaultInnerMethod();

        DefaultModifier.DefaultInnerStaticClass defaultStaticInnerClass
                = new DefaultModifier.DefaultInnerStaticClass();
        defaultStaticInnerClass.defaultInnerStaticClassField = 0;
        defaultStaticInnerClass.defaultInnerStaticClassMethod();

        DefaultModifier.DefaultInnerInterface defaultInnerInterface
                = new DefaultModifier.DefaultInnerInterface() {
            @Override
            public void alwaysPublicAbstractMethod() {
            }
        };

        DefaultOuterInterface defaultOuterInterface = new DefaultOuterInterface() {
            @Override
            public void alwaysPublicAbstract() {
            }
        };



        /*Private*/
        //PrivateModifier privateModifier = new PrivateModifier();// стоит приватный конструктор

    }
}

