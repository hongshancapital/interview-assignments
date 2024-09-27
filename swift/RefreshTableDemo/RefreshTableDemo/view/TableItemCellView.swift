//
//  TableItemCellView.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//

import Foundation
import SwiftUI

struct TableItemCellView: View{
    
    @Binding var itemJson: ItemCellDataJson
    @Environment(\.colorScheme) private var colorScheme
    
    var body: some View {
        Group{
            HStack(
                alignment: .center, 
                spacing: 0) { 
                    HStack(alignment: .top, spacing: 0) { 
                        NetImageView(url: itemJson.artworkUrl60, placeImage: nil)
                            .frame(width: 50, height: 50, alignment: .center)
                            .background(Color.clear)
                            .cornerRadius(8)
                        
                        rowTextLabel
                    }
                    
                    Spacer()
                    
                    CollectItemButton(isCollection: .init(get: { 
                        itemJson.isCollection == true
                    }, set: { value in
                        itemJson.isCollection = value
                        if value {
                            CachesData.addItem(data: itemJson)
                        }else {
                            CachesData.removeItem(data: itemJson)
                        }
                    }))
            }
        }
        .padding(12)
        .background(Color(uiColor: .secondarySystemBackground))
        .cornerRadius(8)
    }
    
    var rowTextLabel: some View {
        
        VStack(alignment: .leading, spacing: 0) { 
            Text(itemJson.trackCensoredName)
                .font(Font.system(size: 14, weight: .bold, design: .rounded))
                .lineLimit(1)
                .foregroundColor(Color(.label))
                .padding(.bottom, 4)
            
            Text(itemJson.description)
                .lineLimit(2)
                .font(.system(size: 13))
                .foregroundColor(Color.init(.secondaryLabel))
            
        }
        .padding(.leading, 10)
        
    }
}
