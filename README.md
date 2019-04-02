# TCache

简单的易用的基于okhttp的缓存框架

# 使用方法

使用okhttp做为网络请求库，并在okhttp初始化时，添加缓存拦截器：示例如下：

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new TCacheInterceptor(DemoApplication.getInstance()))
                .build();

当一个网络请求需要缓存时，只需要在请求头添加一个变量即可。
示例如下，方法不唯一，只需要请求头添加use_cache: CACHE_DEFAULT即可：

缓存的策略的选择主要根据开发者在对应api设置的请求头参数use_cache的值进行判断，它的值主要有以下四种。没有对应的参数则视为不启用缓存。
# CACHE_DEFAULT：
默认的缓存策略，第一次访问api是进行网络数据的请求，网络数据请求成功之后，缓存数据，下次调用这个api的时候直接从缓存中读取对应的数据进行返回。默认超时时间1天。
# CACEH_NONET：
断网启用缓存，第一次访问api是进行网络数据的请求，网络数据请求成功之后，缓存数据，下次调用这个api的时如果网络状态不佳或者网络请求失败，则从从缓存中读取对应的数据进行返回。
# CACHE_WEB：
根据web的缓存协议进行网络数据的缓存。根据Cache-Control参数的值的不同会有不同的结果。如果为：public，private，must-revalidation，proxy-revalidation则进行网络缓存，缓存策略采用CACHE_DEFAULT的策略。如果取值为no-cache和no-store则不进行缓存操作。当值为max-age时，读取后面的超时时间，并设置在缓存数据中，其余策略采用CACHE_DEFAULT。
# CACHE_NO：
不使用缓存，用于数据更新频繁的接口和一些需要提交表单的接口，比如登录，注册等等。

# 使用有问题欢迎提出issue
