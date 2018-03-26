# kotlin-variety-strings

[![Build Status](https://travis-ci.org/alatushkin/kotlin-variety-strings.svg?branch=master)](https://travis-ci.org/alatushkin/kotlin-variety-strings)
[![codecov](https://codecov.io/gh/alatushkin/kotlin-variety-strings/branch/master/graph/badge.svg)](https://codecov.io/gh/alatushkin/kotlin-variety-strings)
[![codebeat badge](https://codebeat.co/badges/4ffecc9a-4322-456b-91e5-d7b9687f64d9)](https://codebeat.co/projects/github-com-alatushkin-kotlin-variety-strings-master)

Utility library to get variety of strings from single string with special syntax

Helps to generate different variants from single template string. 
For example to let your bot send vary messages in same case.

Template syntax is quite simple and obvious:
```kotlin
val randomStr = "[Hi!|Yo!]\n[What can i do for|How can i help] you today?".randomVariant()
```
With this snippet you get one of 4 variant:
* Hi!\nWhat can i do for you today?
* Yo!\nWhat can i do for you today?
* Hi!\nHow can i help you today?
* Yo!\nHow can i help you today?

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
    
To use it:
with Gradle:
```
repositories {
    ....
 maven { url  "https://dl.bintray.com/alatushkin/maven"}
    ....
}
....
dependencies{
...
 compile 'name.alatushkin.utils:kotlin-variety-strings:0.0.1'
...
}
```
