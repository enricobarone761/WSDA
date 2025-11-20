let gestore_sistema = {'nome':'luca', 'password':505}
for (let i = 0; i < 10; i++) {
    console.log(gestore_sistema.nome + '\n' + gestore_sistema.password)
    console.log(gestore_sistema.password + i)
}

let sum = function(x,y){return x+y;};

console.log(sum(85,5));

function MyFunction(){
    console.log('ciao');
    let j = 0;
    for (let i = 0; i < 100; i++) {
        j++
        console.log(sum(i,j));
    }
}
