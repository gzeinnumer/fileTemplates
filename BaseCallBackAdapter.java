package ${PACKAGE_NAME};

public interface BaseCallBackAdapter<T> {
    void onClick(int type, int position, T data);
}