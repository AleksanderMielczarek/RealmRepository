[![](https://jitpack.io/v/AleksanderMielczarek/RealmRepository.svg)](https://jitpack.io/#AleksanderMielczarek/RealmRepository)

# RealmRepository

RealmRepository generates classes with access to [Realm](https://realm.io/) database. Library uses repository pattern, each generated class supports database operations on certain type. 
Project was inspired by [Spring Data JPA](http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html).

Supported functions:

```java
public interface Repository<T extends RealmObject, ID> {

    long countSync();

    @Nullable
    T findOneSync(ID id);

    @Nullable
    T findFirstSync();

    List<T> findAllSync();

    boolean existsSync(ID id);

    T saveSync(T entity);

    List<T> saveSync(Iterable<T> entities);

    void deleteSync(T entity);

    void deleteSync(ID id);

    void deleteSync(Iterable<T> entities);

    void deleteAllSync();

    Single<Long> count();

    Single<T> findOne(ID id);

    Single<T> findFirst();

    Observable<T> findAll();

    Single<Boolean> exists(ID id);

    Single<T> save(T entity);

    Single<List<T>> save(Iterable<T> entities);

    Completable delete(T entity);

    Completable delete(ID id);

    Completable delete(Iterable<T> entities);

    Completable deleteAll();
    
}
```

## Usage

Add it in your root build.gradle:

```groovy
buildscript {  
    repositories {
        ...
        mavenCentral()
    }
    dependencies {
        ...
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'            
    }
}

allprojects {
	repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency:

```groovy
apply plugin: 'android-apt'

dependencies {
    ...
    compile 'com.github.AleksanderMielczarek.RealmRepository:realmrepository:0.2.1'
    apt 'com.github.AleksanderMielczarek.RealmRepository:realmrepository-processor:0.2.1'
}
```

## Example

Create model:

```java
public class User extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private String surname;
    
    //getters and setters
}
```

Create repository by extending generic Repository and annotate it with @RealmRepository. In this example User and String are types of model and primary key.

```java
@RealmRepository
public interface UserRepository extends Repository<User, String> {
}
```

Class with "Impl" suffix will be generated. In this example it will be UserRepositoryImpl. Usage in code:

```java
RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
Realm.setDefaultConfiguration(realmConfiguration);
RealmRepositoryConfiguration repositoryConfiguration = RealmRepositoryConfiguration.getDefault();
UserRepository userRepository = new UserRepositoryImpl(repositoryConfiguration);
```

By default repository will use Realm.getDefault() to obtain Realm instance but it can be changed:

```java
RealmRepositoryConfiguration.builder()
        .withRealmProvider(new RealmProvider() {
            @Override
            public Realm provideRealm() {
            }
        }).build();
```

Library supports auto generation of primary keys. It uses [UUID.randomUUID().toString()](https://developer.android.com/reference/java/util/UUID.html#randomUUID()) internally.

```java
@RealmRepository(autoGenerateId = true)
public interface UserRepository extends Repository<User, String> {
}
```

Support generation of abstract class. Following code will generate abstract class AbstractUserRepository.

```java
@RealmRepository(abstractRepository = true)
public interface UserRepository extends Repository<User, String> {
}
```

## Limitations

- every model must have field with @PrimaryKey
- repository return object that is no longer attached to Realm
- client has to set Scheduler, methods does not operate on particular Scheduler by default
- auto generate of primary keys only for Strings

## Note

In future releases API may change. Feel free to contribute.

## Changelog

### 0.2.1 (2016-11-11)

- update SDK
- update Realm to 2.1.1
- allow to generate abstract class from repository 
- rename get to find method

### 0.2.0 (2016-09-25)

- add sync methods
- fix bugs

## License

    Copyright 2016 Aleksander Mielczarek

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.