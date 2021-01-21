//
//  GoodsRow.swift
//  HJJSearch
//
//  Created by haojiajia on 2020/12/11.
//

import SwiftUI

struct GoodsRow: View {
    var goodsDesc: GoodsDescModel
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            HStack(alignment: .center) {
                VStack(alignment: .leading) {
                    Text(goodsDesc.name)
                        .font(Font.system(size: 16, weight: .semibold, design: .default))
                        .foregroundColor(.primary)

                    Text(goodsDesc.isInStock ? "In-stock" : "Out-of-stock")
                        .font(Font.system(size: 13, weight: .light, design: .default))
                        .foregroundColor(.secondary)
                }
                .padding(.leading, 20)
                
                Spacer()
                Text("$" + String(goodsDesc.price))
                    .font(.caption)
                    .padding(EdgeInsets(top: 3, leading: 9, bottom: 3, trailing: 9))
                    .foregroundColor(goodsDesc.isInStock ? .blue : .gray)
                    .background(goodsDesc.isInStock ? Color(hex: 0xf5f7fD) : Color(hex: 0xf7f7f7))
                    .cornerRadius(9.0)
                    .padding(.trailing, 20)
            }
            .frame(height: 60)
            .background(Color(.systemBackground))
            //.background(colorScheme == .dark ? Color.secondary : Color.white)
            //Divider()
        }
    }
}

struct GoodsRow_Previews: PreviewProvider {
    static var previews: some View {
        let goodsWriteList = [
            GoodsModel(id: 1, category: "Vacuum", brand: "Dyson", items: [GoodsDescModel(id: 11, name: "V11", isInStock: true, price: 599.99), GoodsDescModel(id: 12, name: "V10", isInStock: false, price: 399.99)]),
            GoodsModel(id: 2, category: "Hair Dryer", brand: "Dyson", items: [GoodsDescModel(id: 22,name: "Supersonic", isInStock: true, price: 399.99)])]

        Group {
            GoodsRow(goodsDesc: goodsWriteList[0].items[0])
            GoodsRow(goodsDesc: goodsWriteList[1].items[1])
        }
        .previewLayout(.fixed(width: 300, height: 70))
        .environment(\.colorScheme, .dark)
    }
}
