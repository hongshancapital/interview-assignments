//
//  Product.swift
//  SandD
//
//  Created by qiu on 2021/1/22.
//

import SwiftUI

struct Product: Hashable, Decodable {
    var name: String
    var isInStock: Bool
    var price: String
    var currency: String
}

struct ProductView: View {
    let currencySign: [String:String] = ["USD": "$"]
    var product: Product
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(product.name)
                Text(product.isInStock ? "In-stock": "Out-of-stock")
                    .font(.footnote)
                    .fontWeight(.thin)
            }
            Spacer()
            Button(action: {}) {
                Text("  \(currencySign[product.currency]!)\(product.price)  ")
                    .font(.caption)
                    .padding(5)
                    .background(product.isInStock ?  Color.init(red: 245/255, green: 247/255, blue: 254/255) : Color.gray.opacity(0.08))
            }
            .disabled(!product.isInStock)
            .cornerRadius(40)
        }
    }
}

struct ProductView_Previews: PreviewProvider {
    static var products: [Product] = load("productData.json")
    static var previews: some View {
        Group {
            ProductView(product: products[0])
            ProductView(product: products[1])
        }
        .previewLayout(.fixed(width:300, height: 70))
    }
}
