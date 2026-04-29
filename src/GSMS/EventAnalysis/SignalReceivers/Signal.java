package GSMS.EventAnalysis.SignalReceivers;

public class Signal<T> {
    private T data;
    public Signal(T data) {
        this.data = data;
    }
    public T getSignalData() {
        return data;
    }
}
