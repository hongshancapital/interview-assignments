//
//  RefreshFooterView.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/26.
//

import SwiftUI
import Combine

struct RefreshFooterView: View {
    
    var hasMoreData: Bool
    
    init(hasMoreData: Bool = true) {
        self.hasMoreData = hasMoreData
    }
    
    var body: some View {
        HStack{
            Spacer()
            if hasMoreData {
                ProgressView()
                    .padding(.trailing, 5)
            }
            Text(hasMoreData ? "Loading..." : "No more data")
                .foregroundColor(Color(uiColor: .placeholderText))
            Spacer()
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
}



struct RefreshFooterView_Previews: PreviewProvider {
    static var previews: some View {
        RefreshFooterView()
    }
}
