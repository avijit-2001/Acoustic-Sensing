import UIKit
import Darwin

// This is a comment
var greeting = "Hello, playground"
var str = "some data"
str = "data changed"

print(str)

let con = "data"
print(str)

let N = 100


func dp(idx: Int, rem: Int) -> Int {
    if(idx == N) {return 0}
    var ans = dp(idx: idx+1, rem: rem-1)
    let ans1 = dp(idx: idx , rem: rem)
    ans = max(ans, ans1)
    return ans
}

class Spaceship {
        
}


