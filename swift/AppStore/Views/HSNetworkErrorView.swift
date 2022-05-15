//
//  HSNetworkErrorView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

struct HSNetworkErrorView: View {
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                Image("icon_networkerror")
                Spacer()
            }
            Text(LocalizedStringKey("network_error_text"))
            Spacer()
        }
    }
}

struct HSNetworkErrorView_Previews: PreviewProvider {
    static var previews: some View {
        HSNetworkErrorView()
    }
}
