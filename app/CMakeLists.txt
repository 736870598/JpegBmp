# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

#设置so库路径
set(my_lib_path ${CMAKE_SOURCE_DIR}/libs)

#j将第三方库作为动态库引用
add_library(jpeg
            SHARED
            IMPORTED )

#指名第三方库的绝对路径
set_target_properties( jpeg
                       PROPERTIES IMPORTED_LOCATION
                       ${my_lib_path}/${ANDROID_ABI}/libjpeg.so )

#系统log库
find_library( log-lib log )

#本地的so库
add_library( jpeg_sxy
             SHARED
             src/main/cpp/native-lib.cpp )

target_link_libraries( # Specifies the target library.
                            jpeg_sxy
                            jpeg
                            jnigraphics
                            ${log-lib} )


#如果有c文件需要编译，可以指定编译文件夹下所有的c文件
#file(GLOB my_c_path src/main/cpp/lib/*.c)
#add_library( jpeg_sxy
#             SHARED
#             ${my_c_path}
#             src/main/cpp/native-lib.cpp )
