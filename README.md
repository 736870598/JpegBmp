# JpegBmp
利用jpeg库对图片进行压缩，jpeg库主要利用哈夫曼算法进行压缩处理，对于图片来说，压缩度高，失真度小。

### cmakelist模板：

    cmake_minimum_required(VERSION 3.4.1)
    
    #设置so库路径(如果使用到第三方so库，该路径指向libs文件夹)
        set(my_lib_path ${CMAKE_SOURCE_DIR}/libs )
        

    #系统log库
        find_library( log-lib log  )
        

    #本地的so库
        add_library( jpeg_sxy
                 SHARED
                 src/main/cpp/native-lib.cpp )       
                       
    #将第三方库作为动态库引用
        add_library(jpeg
                SHARED
                IMPORTED  )
                

    #指名第三方so库的路径
        set_target_properties( jpeg
                           PROPERTIES IMPORTED_LOCATION
                           ${my_lib_path}/${ANDROID_ABI}/libjpeg.so  )
                           

    #链接所有使用到的so库
        target_link_libraries( jpeg_sxy
                                jpeg
                                jnigraphics
                                ${log-lib}  )
                                

    #如果有c文件需要编译，可以指定编译文件夹下所有的c文件
    #file(GLOB my_c_path src/main/cpp/lib/*.c)
    #add_library( jpeg_sxy
    #             SHARED
    #             ${my_c_path}
    #             src/main/cpp/native-lib.cpp )



