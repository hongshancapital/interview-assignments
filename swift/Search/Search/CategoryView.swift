//
//  CategoryView.swift
//  Search
//
//  Created by chx on 2021/4/21.
//

import SwiftUI

struct CategoryView: View {
    let category: Categorys
    var body: some View {
        VStack(alignment:.leading){
            Section(header:
                        Text(category.nameText)
                        .padding(.leading,20)
                        .frame(height:40)
                        .font(.system(size: 20, weight: .medium, design: .default))
                        .foregroundColor(Color.gray)) {
                ForEach(category.goodsArray){ good in
                    GoodsCell.init(good: good)
                        .background(Color.white)
                }
            }
            .frame(width: UIScreen.main.bounds.size.width, alignment: .leading)
            .background(backColor)
        }
    }
}

struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        CategoryView(category: Model.getSearchModel()![0])
    }
}
