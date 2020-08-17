![BeerApp](images/ic_launcher.round.png)

# Introducción

BeerApp, aplicación de cervezas utilizando arquitectura Clean, usando como patrón de presentación MVVM a través de un único estado.

# Algunas de las características más importantes

- Arquitectura Clean
- Dagger como inyector de dependencias
- Patrón de presentación MVVM (Jetpack Viewmodel)
- Empleo de corutinas y Flow para tratar multihilo
- Room como almacenamiento de datos, puede funcionar offline
- MotionLayout para las animaciones
- Testing con Junit y Mockito
- Navigation (Jetpack)
- Moshi para el parseo del Json

## Estructura del proyecto


Es una arquitectura clean, siguiendo un patrón MVVM de un único estado.

Gradle se estructura:

-A nivel de proyecto: Aquí se definen las bibliotecas y cada una de las versiones
-A nivel de módulo: Se utilizan alias para implementar cada una de estas versiones

Se separa el concepto de versión / implementación


El código se divide en varios módulos:

app : Módulo principal de la aplicación, representa la capa mas externa incluyendo las vistas, viewmodel e implementaciones concretas del framework. Ve al resto de módulos mencionados abajo

usecases: Casos de uso del app que separa la capaa de la lógica de dominio del flujo de vistas del app

data : Capa de datos, formada por el BeerRepository y las interfaces de los datasources y red. Las fuentes de datos será la local (LocalDataSource que implementa RoomDataSource), y la remota (RemoteDataSource que implementa RetrofitDataSource)

domain: Clases del dominio; Beer, Either y Failure y constantes

testShared: Mock común para testing

Los estados asociados a cada una de las pantallas se muestra mediante una clase sellada, y representa el estado de la pantalla en ese momento:


```
sealed class BeerListState {
        class Error(val failure: Failure) : BeerListState()
        class ShowItems(val beerList: List<UIBeer>) : BeerListState()
        class IsLastPage(val isLastPage: Boolean) : BeerListState()
        class IsLoading(val isLoading: Boolean) : BeerListState()
    }
```

Así se puede mostrar un Loader, un error que pueda ocurrir y el estado de las cervezas.
Se gestionan todos los posibles errores que, por ejemplo, a nivel de testing puedan ocurrir:

RoomDataSource.EmptyList ->  Lista vacía a la hora de recuperarla en el localDataSource

NetworkManager.UnexpectedServerError -> Código de error que puede devolver el servidor en caso de que el servicio falle, devuelve un código de error y un mensaje
NetworkManager.ServerResponseException -> Excepción del servidor, devuelve un código de error y un mensaje
NetworkManager.EmptyBody -> Cuerpo de mensaje del server vacío

RetrofitDataSource.ConnectionError -> Error de conexión con el servicio

Failure.JsonException -> Error de parseo en el Json, se muestra un mensaje de error
JsonMapper.JsonSyntaxException -> Error de mapeo de datos
NullResult -> null

Este consumo de APIs se hace a través del patrón repository BeerRepository. Lleva un manejador de conexiones para verificar que existe conexión y se envían los datos asociados y las llamadas correspondientes definidas en ApiService
Se hace uso de dos datasources: 

- RoomDataSource que implementa LocalDataSource. Se obtienen los datos locales
- RetrofitDataSource que implementa RemoteDataSource. Realiza la petición mediante retrofit y siempre se almacenan los datos


Siempre se recogen los datos de local. 
Después de una petición se almacenan y se devuelve el flow de la base de datos, de esa manera siempre que cambie un dato, ambas pantallas se actualizarán automáticamente


Las respuestas llevan un Either como devolución en las llamadas: Ejemplo


```
        override suspend fun <T, R> safeRequest(
            callRequest: Response<T>,
            functionCall: (Either.Right<T>) -> Either<Failure, R>
        ): Either<Failure, R> {


            return ((if (callRequest.isSuccessful) {
                val body = callRequest.body()

                if (body != null)
                    Either.Right(body)
                else
                    Either.Left(EmptyBody())

            } else {
                when (callRequest.code()) {
                    in 300..600 -> Either.Left(
                        ServerResponseException(
                            callRequest.code(),
                            callRequest.errorBody()?.string()
                        )
                    )
                    else -> Either.Left(
                        UnexpectedServerError(
                            callRequest.code(),
                            callRequest.errorBody()?.string()
                        )
                    )
                }
            }).flatMapToRight { rightResult -> functionCall.invoke(Either.Right(rightResult)) })

        }

```

Either es un monad, unión disjuntiva, devuelve siempre un valor, o bien la clase izquierda (en cuyo caso será un fallo) o bien la derecha (entonces será el dato que buscas). 
Esto es así ya que de esta forma se controla cada uno de los fallos posibles a la hora de devolver la petición; la estructura permite un modelo abierto de códigos de error de petición o extender de la clase abstracta CustomFailure para decidir qué casos de uso tienen que errores.

Luego cada capa tiene un modelo diferente: Beer tiene: un modelo de UI , un modelo del servidor entity, sin por ejemplo el campo de disponibilidad, un modelo de dominio y otro de base de datos

Cada capa tiene su conversor que permite gestionar conversiones de un lado a otro, de forma que los cambios que pueda haber en backend no afecten al resto de la aplicación, y viceversa.



#Testing

Se ha realizado Unit testing del repositorio, casos de uso y un par de test del BeerListViewModel

![test1]("images/test1.png")
![test1]("images/test2.png")
![test1]("images/test3.png")


# Algunas características

##Lista y detalle con animaciones
![lista y detalle]("images/image1.png")
![lista y detalle]("images/image2.png")
![lista y detalle]("images/image3.png")
![lista y detalle]("images/image4.png")

##Modo oscuro
[oscuro]("images/darkmode1.png")
[oscuro]("images/darkmode2.png")

##Rotación en cualquier punto
[rotacion]("images/darkmoderotate1.png")
[rotacion]("images/darkmoderotate2.png")
