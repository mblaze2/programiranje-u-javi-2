Objects serialize into an object graph

##### Fail-fast mechanism
Write code that fails on its first use rather than possibly doing so at an undetermined time later due to the same issue.

##### Decorator pattern
In object-oriented programming, the **decorator pattern** is a design pattern that allows behavior to be added to an individual object, dynamically, without affecting the behavior of other objects from the same class.

##### Transient variables
Transient variables are not serialized or deserialized.
For extended classes, the parent object is implicitly transient.

##### VM
The JVM can call private methods on any class.

##### Serialization
During serialization the private method `writeObject` is accessed through reflection if it exists.
The `Serializable` interface is a *Marker* and *Mixin* interface.
It is recommended to use the `.ser` extension.
Implementing the interface complicates development, and should not be taken lightly.

##### Deserialization
Should be treated as a constructor, needing validation.

##### Defensive copy
A copy of an object that can

##### Marker interfaces
Marker interfaces signal to the compiler that the classes that implement them that they have certain possibilities. They **do not** implement any fields or methods.

##### Mixin interface
Mixing interface describe optional behaviours of a class.
Examples: Comparable, Serializable, ...