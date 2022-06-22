//
//  ListV.swift
//  MyHomework
//
//  Created by mao on 2022/6/10.
//

import SwiftUI

struct ListV: View {
    var body: some View {
        NavigationView{
          
            List(myListData){ listData in
                NavigationLink {
                    
                } label: {
                    ForEach(0..<1,id : (\.self)){ num in
                        let current : Int = Int(listData.id)!;
                       let listData2 = myListData[current]
                        MylistRow(myObjc: listData2)
                    }
                }
                
            }
            .navigationTitle("你好呀")
    
            .refreshable{
                
            }
        }
    }
}

struct ListV_Previews: PreviewProvider {
    static var previews: some View {
        ListV()
    }
}
