# kotlin-variety-strings
Utility library to get variety of strings from single string with special syntax

Helps to generate different variants from single template string. 
For example to let your bot send vary messages in same case.

Template syntax is quite simple and obvious:
```kotlin
val randomStr = "[Hi!|Yo!]\n[What can i do for|How can i help] you today?".randomVariant()
```
With this snippet you get one of 4 variant.

Also you can use it directly without using String.extension function:
```kotlin
@Throws(ParseException::class)
fun parse(str: String, openChar: Char = '[', closeChar: Char = ']', orChar: Char = '|'): TreeNode 
```
And example:
```kotlin
import name.alatushkin.utils.variety.parse
.....
val rootNode:TreeNode = parse("[Hi!|Yo!]\n[What can i do for|How can i help] you today?")
val variantsPossible:Int=rootNode.count
val someStringVariant:String=rootNode.variant(1)

```
 
 Note:
 There is naive caching inside String.extension functions so it's better to no use them in case of massive users input to avoid OOM
    