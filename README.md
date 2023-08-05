# nvidia-smi-java

## Usage
### Just once
```java
NvidiaSMIExecutor.executeNvidiaSMI(new NvidiaSMIExecutor.Callback() {
@Override
public void onSuccess(JSONObject data) {
        System.out.println("Data: " + data);
        }

@Override
public void onError(String errorMessage) {
        System.err.println("Error: " + errorMessage);
        }
});
```
### Every second
```java
NvidiaSMIExecutor.executeNvidiaSMI(new NvidiaSMIExecutor.Callback() {
    @Override
    public void onSuccess(JSONObject data) {
        System.out.println("Data: " + data);
    }

    @Override
    public void onError(String errorMessage) {
        System.err.println("Error: " + errorMessage);
    }
}, 1);
```