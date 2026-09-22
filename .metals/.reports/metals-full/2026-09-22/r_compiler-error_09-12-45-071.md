error id: 21093BF6A55764CC0E624E0800FACC6F
file:///C:/Users/chidi/Desktop/Scala_Project/Main.scala
### java.lang.NullPointerException: Cannot invoke "scala.meta.internal.pc.CompilerWrapper.compiler()" because "access" is null

occurred in the presentation compiler.



action parameters:
uri: file:///C:/Users/chidi/Desktop/Scala_Project/Main.scala
text:
```scala
import model.*

@main
def main(): Unit =
  println("====================================")
  println("        TEXAS HOLD'EM")
  println("        Poker Simulator")
  println("====================================")
  println()

  println(s"Deck size: ${Deck.size}")
  println()
  println("First 5 cards:")

  Deck.standar
```


presentation compiler configuration:
Scala version: 3.3.8-bin-nonbootstrapped
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.8\scala3-library_3-3.3.8.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.18\scala-library-2.13.18.jar [exists ]
Options:





#### Error stacktrace:

```
dotty.tools.pc.ScalaPresentationCompiler.didChange$$anonfun$1(ScalaPresentationCompiler.scala:533)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:149)
	scala.meta.internal.pc.CompilerAccess.withNonInterruptableCompiler$$anonfun$1(CompilerAccess.scala:133)
	scala.meta.internal.pc.CompilerAccess.onCompilerJobQueue$$anonfun$1(CompilerAccess.scala:210)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:153)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

java.lang.NullPointerException: Cannot invoke "scala.meta.internal.pc.CompilerWrapper.compiler()" because "access" is null