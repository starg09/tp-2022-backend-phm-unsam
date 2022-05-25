> **_Nota:_** Ejecutar todo desde la raiz del proyecto

## Crear carpetas

```bash
mkdir mongoShards
cd ./mongoShards
mkdir cfg1 cfg2 shard1 shard2 repl1 repl2
sudo chmod 775 shard* cfg*
sudo chown -R `id -un` shard* cfg*
```

## Config Servers

```bash
mongod --replSet rsConf --configsvr --port 26050 --logpath ./mongoShards/log.cfg1 --logappend --dbpath ./mongoShards/cfg1

mongod --replSet rsConf --configsvr --port 26051 --logpath ./mongoShards/log.cfg2 --logappend --dbpath ./mongoShards/cfg2
```

## Shards y Replicas

```bash
mongod --shardsvr --replSet shard1 --dbpath ./mongoShards/shard1 --logpath ./mongoShards/log.shard1 --port 27000 --logappend --oplogSize 50

mongod --shardsvr --replSet shard1 --dbpath ./mongoShards/repl1 --logpath ./mongoShards/log.repl1 --port 27001 --logappend --oplogSize 50

mongod --shardsvr --replSet shard2 --dbpath ./mongoShards/shard2 --logpath ./mongoShards/log.shard2 --port 27100 --logappend --oplogSize 50

mongod --shardsvr --replSet shard2 --dbpath ./mongoShards/repl2 --logpath ./mongoShards/log.repl2 --port 27101 --logappend --oplogSize 50
```

> Verificacion de procesos corriendo: `ps -fe | grep mongo`

## Levantar Servicio de routeo

```bash
mongosh --port 26050
```

```
cfg={_id:"rsConf",members:[{_id:0 ,host: "127.0.0.1:26050"}, {_id: 1, host: "127.0.0.1:26051" }]}
rs.initiate(cfg)
exit
```

## Configuracion de Sharding

### Shard 1

```bash
mongosh --port 27000
```

```
cfg={_id:"shard1", members:[{_id:0 ,host: "127.0.0.1:27000"}, {_id:1 ,host: "127.0.0.1:27001" }]}
rs.initiate(cfg)
rs.status()
exit
```

### Shard 2

```bash
mongosh --port 27100
```

```
cfg={_id:"shard2", members:[{_id:0 ,host: "127.0.0.1:27100"}, {_id:1 ,host: "127.0.0.1:27101" }]}
rs.initiate(cfg)
rs.status()
exit
```

## Iniciar Sharding

```bash
mongos --configdb rsConf/127.0.0.1:26050,127.0.0.1:26051 --logappend --logpath ./mongoShards/shardlog --port 28001 --bind_ip 127.0.0.1
```

## Conexión al Router

```bash
mongosh --port 28001
```

```
sh.addShard("shard1/127.0.0.1:27000")
sh.addShard("shard2/127.0.0.1:27100")


db.adminCommand( { listShards: 1 } )

use prueba
db.productos.createIndex({"_id": "hashed"})
db.lotes.createIndex({"_id": "hashed"})

sh.enableSharding("prueba")

sh.shardCollection("prueba.productos", {"_id": "hashed"}, false)
sh.shardCollection("prueba.lotes", {"_id": "hashed"}, false)

db.productos.getShardDistribution()
db.lotes.getShardDistribution()
```