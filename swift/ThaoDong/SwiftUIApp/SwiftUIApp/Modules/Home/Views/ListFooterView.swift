//
//  ListFooterView.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import SwiftUI

struct ListFooterView: View {
    var finished: Bool
    var show: Bool
    var body: some View {
        HStack {
            Spacer()
            if finished {
                Text("NoMore")
                    .fontWeight(.bold)
                    .foregroundColor(.gray)
            }
            else {
                if show {
                    ProgressView()
                    Text("Loading")
                        .fontWeight(.medium)
                        .foregroundColor(.gray)
                }
            }
            Spacer()
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)


    }
}
