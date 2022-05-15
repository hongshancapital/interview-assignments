//
//  HSEmptyView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

struct HSEmptyView: View {
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                Image("icon_empty")
                Spacer()
            }
            Spacer()
        }
    }
}

struct HSEmptyView_Previews: PreviewProvider {
    static var previews: some View {
        HSEmptyView()
    }
}
