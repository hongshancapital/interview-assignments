//
//  ProductRow.swift
//  HongShanApp
//
//  Created by wangbin on 2021/10/22.
//

import SwiftUI

struct ProductRow: View {
    var productModel: ProductModel
    var body: some View {
        HStack{
            
            VStack(alignment: .leading){
                Text(productModel.name)
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .foregroundColor(.black)
                Text(productModel.description)
                    .font(.subheadline)
                    .foregroundColor(.gray)
                
            }
            Spacer()
            Text("$\(productModel.price)")
                .foregroundColor(.blue)
            
        }
        .padding(.all)
    }
}

struct ProductRow_Previews: PreviewProvider {
    static var previews: some View {
        let productM:ProductHeaderModel = headerModels[0]
        ProductRow(productModel:productM.products[0])
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
