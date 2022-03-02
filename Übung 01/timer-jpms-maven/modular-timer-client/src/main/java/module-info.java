import swt6.modular.beans.TimerProvider;

module swt.modular.clients {
    requires swt.modular.beans;
    requires org.slf4j;
    uses TimerProvider;
}