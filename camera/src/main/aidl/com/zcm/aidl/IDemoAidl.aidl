// IDemoAidl.aidl
package com.zcm.aidl;

// Declare any non-default types here with import statements
import com.zcm.aidl.Person;
interface IDemoAidl {
    void addPerson(in Person person);

    List<Person> getPersonList();
}
