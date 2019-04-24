# Hair Salon Application
## Description

![](screenshot/pic1.png)
![](screenshot/pic3.png)
![](screenshot/pic4.png)
![](screenshot/pic5.png)


```
This is a spark application where a manager adds all his stylists and clients. The manager can either update or delete a stylist or a client.```



## Contributors
```
The contributors of this project were:
* Jackline Gatwiri (jaciegatz@gmail.com)
```
## Technologies used
```
* Java.
* Spark - the front end framework.
* postgres
```
## B-D-D
```

| INPUT                           | BEHAVIOR                                         | OUTPUT                                                                    |
|---------------------------------|--------------------------------------------------|---------------------------------------------------------------------------|
| The user loads the application  | The applications gets the index file             | The user is given the landing page                                        |
| The user clicks "Check stylist" | The application checks for the existing stylists | The user is provided with a list of the available stylists.               |
| The user clicks "add stylist"   | The application fetches the input forms          | The user is prompted to add a stylist.                                    |
| The user clicks a stylist       | The application fetches the stylist's details    | The user sees the stylist's clients and their details                     |
| The user clicks "Add client"    | The application gets the input forms             | The user is prompted to add a client                                      |
| The user click the trash icon   | The application runs the delete function         | The user deletes the client or stylist depending on where he/she clicked. |
```
## Know bugs
```
Some of the known bugs include:
* The background image not loading
* 500 internal error( Unable to find some vtl files)
```
## Licencing
```
[MIT License](https://choosealicense.com/licenses/mit/)

```
