@echo off

cd ..\..\..\out\production\LambdaLib2\
javah -verbose -jni -classpath .;..\..\..\src\editor\cpp\deps\* -o ..\..\..\src\editor\cpp\cn_lambdalib2_vis_editor_ImGui.h cn.lambdalib2.vis.editor.ImGui 
cd ..\..\..\src\editor\cpp

pause

@echo on