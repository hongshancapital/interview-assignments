//
//  GoodsCell.swift
//  Search
//
//  Created by chx on 2021/4/21.
//

import SwiftUI


struct GoodsCell: View {
    let good : Goods
    var body: some View {
        HStack(spacing:0){
            VStack(alignment:.leading, spacing:0){
                Text(good.nameText)
                    .font(Font.system(size: 15, weight: .medium, design: .default))
                    .lineLimit(1)
                Text(good.descText)
                    .font(Font.system(size: 12))
                    .foregroundColor(.gray)
                    .padding(.top,5)
            }.padding(.leading,20)
            Spacer()
            Text(good.priceText)
                .font(.system(size: 12))
                .foregroundColor(good.priceViewColor)
                .background(lightGrayColor)
                .cornerRadius(5)
                .padding(.trailing,20)
                            
        }
        .padding(.top,10)
    }
}

struct GoodsCell_Previews: PreviewProvider {
    static var previews: some View {
        GoodsCell(good:Model.getSearchModel()![0].goods![1])
    }
}
