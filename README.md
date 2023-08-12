# nvidia-smi-java

![mvn-checkup status](https://github.com/maifeeulasad/nvidia-smi-java/actions/workflows/mvn-checkup.yml/badge.svg)

## Usage
### Just once
```java
NvidiaSMIWrapper.executeNvidiaSMI(new NvidiaSMIWrapper.Callback() {
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
NvidiaSMIWrapper.executeNvidiaSMI(new NvidiaSMIWrapper.Callback() {
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