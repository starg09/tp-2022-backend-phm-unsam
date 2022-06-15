# backend-tp-2022-phm-grupo-2

## Configuración Sharding

Ver [configSharding.md](configSharding.md)

## Consultas solicitadas en Mongo

### 1)  Saber qué producto es el más clickeado.

```
[{
 $group: {
  _id: {
   nombreProducto: '$nombreProducto'
  },
  count: {
   $sum: 1
  },
  nombreProducto: {
   $first: '$nombreProducto'
  }
 }
}, {
 $sort: {
  count: -1
 }
}, {
 $limit: 1
}]
```
### 2)  Saber cuántos artículos son pisos de alto tránsito.

``` 
db = db.getSiblingDB("prueba");
db.getCollection("productos").aggregate(
    [
        { 
            "$match" : { 
                "esAltoTransito" : { 
                    "$eq" : true
                }
            }
        }, 
        { 
            "$count" : "esAltoTransito"
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);
```

### 3)  Saber qué artículos tienen más de 4 lotes disponibles.

```
[{
 $lookup: {
	from: 'lotes',
	localField: 'lotes',
	foreignField: '_id',
	as: 'lotes_lookup'
 }
}, {
    $addFields: {
	lotes_lookup: {
    $filter: {
		input: '$lotes_lookup',
        as: 'item',
        cond: {
            $gt: [
                '$$item.cantidadDisponible',
                0
            ]
        }
        }
	    }
    }
}, {
    $addFields: {
        cantidadLotesConStock: {
            $size: '$lotes_lookup'
        }
    }
}, {
    $addFields: {
        masDeCuatroLotesConStock: {
         $gte: [
            '$cantidadLotesConStock',
            4
         ]
        }
    }
}, {
    $match: {
	    masDeCuatroLotesConStock: true
    }
}]
```

## Consultas solicitadas en Neo4J

### 1)  Saber qué usuarios compraron un determinado producto y lote.

```
MATCH (user:Usuario)-[:COMPRO {nombreProducto:"Acme rustico", numeroLote: 1111}]->(:Compra) RETURN user
```

### 2)  Saber una sugerencia de productos adicionales para un
### determinado producto que quiero comprar. Esto ocurre cuando
### otros usuarios compraron dichos artículos y otros más en la misma
### orden de compra.

```
WITH "Jill" as nombreUsuario, "combo" as productoComprado
MATCH 
(a:Usuario {nombre: nombreUsuario})-[itemCompraA:COMPRO {nombreProducto: productoComprado}]->(ordenA:Compra), 
(b:Usuario)-[itemOtraCompra:COMPRO {nombreProducto: productoComprado}]->(ordenB:Compra),
(b)-[recomendacionCompra:COMPRO]->(ordenB)
WHERE a <> b AND recomendacionCompra <> itemOtraCompra
RETURN recomendacionCompra.nombreProducto as `Nombre Producto`, recomendacionCompra.numeroLote as `Numero De Lote`
```

### 3)  Saber que usuario tiene una compra con más de 5 productos.

```
MATCH (user:Usuario)-[item:COMPRO]->(compra:Compra) WITH COUNT (item) AS cantItems, user, compra WHERE cantItems >= 1 RETURN DISTINCT(user.nombre)
```