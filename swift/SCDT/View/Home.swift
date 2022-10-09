//
//  Home.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import SwiftUI

struct Home: View {
    @EnvironmentObject var viewModel: ApplicationViewModel
    var body: some View {
        AppList().environmentObject(self.viewModel)
    }
}

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        Home().environmentObject(ApplicationViewModel())
    }
}
