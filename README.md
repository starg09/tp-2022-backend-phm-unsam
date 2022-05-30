# backend-tp-2022-phm-grupo-2

## Configuración Sharding

Ver [configSharding.md](configSharding.md)

## Consultas solicitadas

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