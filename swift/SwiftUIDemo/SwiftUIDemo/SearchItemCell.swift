//
//  SearchItemCell.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import SwiftUI

struct SearchItemCell: View {
    
    var item: SearchResultModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            HStack(alignment: .center) {
                VStack(alignment: .leading) {
                    Text(item.name)
                    Spacer()
                    Text(item.isInStockString).foregroundColor(Color.gray)
                }
                Spacer()
                VStack(alignment: .trailing, spacing: 10) {
                    Text(item.price.toMoneyStr())
                        .padding(.trailing)
                        .foregroundColor(item.isAvailable ? Color.blue : Color.init(white: 0.3))
                        .background(Color.init(white: 0.7))
                        .cornerRadius(5)
                    
                }
            }
        }
    }
}

struct SearchItemCell_Previews: PreviewProvider {
    static var previews: some View {
        SearchItemCell(item: SearchResultModel())
    }
}

