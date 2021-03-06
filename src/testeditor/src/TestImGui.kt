import cn.lambdalib2.render.*
import cn.lambdalib2.util.Colors
import cn.lambdalib2.vis.editor.ImBoolRef
import cn.lambdalib2.vis.editor.ImGui
import cn.lambdalib2.vis.editor.ImGuiDir
import cn.lambdalib2.vis.editor.ObjectInspection
import cn.ll2test.common.OfflineTestUtils
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11.*
import org.lwjgl.util.vector.Vector2f

object TestImGui {
    enum class TestEnum {
        Fly, Walk, Dive, Crawl
    }

    class TestInner {
        val x = 5
        val y = 6
        val z = 7
        val xxddr = "The earth is piercing like a rainbow"
        val aVector2f = Vector2f()
    }

    class TestInspection {
        var x = 5
        var y = 6
        var z = 7.5f
        var testEnum = TestEnum.Fly
        var aDouble = 1.4142424242
        val aColor = Colors.white()
        var aResLoc: ResourceLocation = ResourceLocation("minecraft", "wtf")
        var aTestInner = TestInner()
        val aVector2f: Vector2f? = null
    }

    @JvmStatic
    fun main(args: Array<String>) {
        OfflineTestUtils.hackNatives()

        Display.setDisplayMode(DisplayMode(1280, 720))
        Display.create()

        val tex = Texture2D.loadFromResource(
            "/testeditor/miku.png",
            TextureImportSettings(
                TextureImportSettings.FilterMode.Blinear, TextureImportSettings.WrapMode.Clamp
            )
        )

        var testChkbox = false
        var testEnum = TestEnum.Crawl
        var testFloat = 0.0f
        var testFloat2 = floatArrayOf(1.0f, 2.0f)
        var testInt = 1
        var testInt2 = intArrayOf(0, 0)
        var testAngle = 0.0f
        var testColor = Colors.white()
        var testText = "A quick brown fox"
        var testTextMult = "#include <iostream>\nusing"
        val openAbout = ImBoolRef(false)

        while (!Display.isCloseRequested()) {
            glViewport(0, 0, 1280, 720)
            glClearColor(0.1f, 0.1f, 0f, 0f)
            glClear(GL_COLOR_BUFFER_BIT)

            val inputs = run {
                val ret = ArrayList<Char>()
                while (Keyboard.next()) {
                    if (Keyboard.getEventKeyState()) {
                        var chr = Keyboard.getEventCharacter()
                        if (!chr.isISOControl())
                            ret += chr
                    }
                }
                ret.toCharArray()
            }

            Mouse.poll()
            val dwheel = Mouse.getDWheel().toFloat() * 0.1f

            ImGui.newFrame(dwheel, inputs)

            // Show demo window
            ImGui.showDemoWindow(true)

            // Show java window
            ImGui.begin("My Test Window", false)
            ImGui.text("Some Text")
            ImGui.labelText("Label", "Text")
            ImGui.textColored(Colors.fromRGB32(0xEE22CCFF.toInt()), "Text Colored")
            ImGui.textWrapped("asldkfjaksldfjaksld jfasl;kdfj a;sldkjfa;sldjv ao[sidf jaopsdfj[aisdf Some wrapped text")

            ImGui.separator()
            ImGui.text("After separator")
            ImGui.newLine()
            ImGui.text("After newLine")
            ImGui.spacing()
            ImGui.text("After spacing")

            ImGui.bulletText("Hello bullet text!")
            ImGui.button("Button")
            if (ImGui.button("Sized button", Vector2f(100f, 100f))) {
                println("I'm clicked")
            }
            ImGui.arrowButton("Arrow Button", ImGuiDir.Left)
            ImGui.arrowButton("Arrow Btn2", ImGuiDir.Down)

            ImGui.image(tex.textureID, Vector2f(100f, 100f))
            ImGui.imageButton(tex.textureID, Vector2f(50f, 50f))

            testChkbox = ImGui.checkbox("Checkbox", testChkbox)

            ImGui.radioButton("RadioButton", true)

            val ix = ImGui.combo("TestEnum", testEnum.ordinal,
                TestEnum.values().map { it.name }.toTypedArray())
            testEnum = TestEnum.values()[ix]

            testFloat = ImGui.sliderFloat("SliderFloat", testFloat, 0.0f, 10.0f)
            ImGui.sliderFloat2("SliderFloat2", testFloat2, 0.0f, 10.0f)
            testInt = ImGui.sliderInt("SliderInt", testInt, 0, 24)

            testAngle = ImGui.sliderAngle("TestAngle", testAngle)
            testInt = ImGui.inputInt("InInt", testInt)
            ImGui.inputInt2("InInt2", testInt2)

            ImGui.colorEdit4("ColorEdit4", testColor)
            ImGui.colorButton("ColorButton", testColor)
            testText = ImGui.inputText("Text", testText)
            testTextMult = ImGui.inputTextMultiline("TextMult", testTextMult)

            testFloat = ImGui.inputFloat("InFloat", testFloat)
            ImGui.inputFloat2("InFloat2", testFloat2)

            if (ImGui.treeNode("Hello %s %s %s", 0, 1, 2)) {
                ImGui.labelText("A", "1")
                ImGui.labelText("B", "asldkfjaslkc")
                ImGui.labelText("C", "66.6666")

                ImGui.treePop()
            }

            if (ImGui.collapsingHeader("CollapsingHeader")) {
                ImGui.labelText("A", "1")
                ImGui.labelText("B", "asldkfjaslkc")
                ImGui.labelText("C", "66.6666")
                ImGui.text("Some")
                ImGui.sameLine(); ImGui.button("Some")
                ImGui.sameLine(); ImGui.button("Horizontal")
                ImGui.sameLine(); ImGui.button("Layout")
            }

            if (ImGui.beginMainMenuBar()) {
                if (ImGui.beginMenu("File")) {
                    if (ImGui.menuItem("Open")) {
                        println("Oh!!")
                    }
                    if (ImGui.menuItem("Save")) {}

                    ImGui.endMenu()
                }
                if (ImGui.beginMenu("About")) {
                    ImGui.menuItem("Hi...", openAbout, true)
                    ImGui.endMenu()
                }
                ImGui.endMainMenubar()
            }

            ImGui.end()

            if (openAbout.value) {
                openAbout.value = ImGui.begin("About", openAbout.value)

                ImGui.text("ImGui LambdaLib2 binding by WeAthFolD")
                ImGui.text(":-)")

                ImGui.end()
            }

            testInspection()

            ImGui.render()

            val error = glGetError()
            if (error != GL_NO_ERROR) {
                println("Err: $error")
            }

            Display.update()
        }

        Display.destroy()
    }

    val inspection = ObjectInspection()
    val obj = TestInspection()

    fun testInspection() {
        ImGui.begin("Test inspection", false)
        inspection.inspect(obj)
        ImGui.end()
    }

}