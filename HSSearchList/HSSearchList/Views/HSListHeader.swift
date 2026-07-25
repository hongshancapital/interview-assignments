//
//  HSListHeader.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import SwiftUI

struct HSListHeader: View {
    var title: String
    var body: some View {
        if #available(iOS 15.0, *) {
            getView().listRowSeparator(.hidden)
        } else {
            getView().listStyle(SidebarListStyle())
        }
    }
    
    func getView() -> some View {
        HStack(alignment: .top, spacing: 0) {
            VStack(alignment: .leading, spacing:0) {
                Spacer()
                Text(title)
                    .font(.system(size: 18))
                    .fontWeight(.regular)
                    .foregroundColor(titleColor)
                Spacer()
            }
            Spacer()
        }
    }
    
}
