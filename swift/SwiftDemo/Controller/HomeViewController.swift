//
//  HomeViewController.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/11.
//

import UIKit

class HomeViewController: UIViewController {
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var headerView: UIView!
    var modelList: [HomeModel] = []
    var dataList: [HomeModel] = []
    var currentPage = 1
    var pageSize = 10
    
    override func viewDidLoad() {
        super.viewDidLoad()

        loadLocalData()
        self.tableView.register(UINib(resource: R.nib.homeCell), forCellReuseIdentifier: HomeCell.reuseId)
        self.tableView.tableFooterView = nil
        self.tableView.tableHeaderView = headerView
        self.tableView.mj_header = MJRefreshNormalHeader(refreshingBlock: { [weak self] in
            self?.headerRefresh()
        })
        self.tableView.mj_header?.beginRefreshing()
    }
}

extension HomeViewController: UITableViewDelegate, UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return modelList.count
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: HomeCell.reuseId) as? HomeCell else { return UITableViewCell() }
        let model = modelList[indexPath.row]
        cell.model = model
        cell.onButtonCompleted = { [weak self] (flag) in
            model.isCollectionFlag = !(model.isCollectionFlag ?? false)
            self?.tableView.reloadRows(at: [indexPath], with: .none)
        }
        if model.isCollectionFlag == true {
            cell.collectionButton.setImage(UIImage(systemName: "heart.fill"), for: .normal)
            cell.collectionButton.tintColor = UIColor.red
        } else {
            cell.collectionButton.setImage(UIImage(systemName: "heart"), for: .normal)
            cell.collectionButton.tintColor = UIColor.systemGray2
        }
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        guard let statusBarHeight = UIApplication.shared.windows.first?.windowScene?.statusBarManager?.statusBarFrame.size.height else { return }
        let offsetY = scrollView.contentOffset.y
        if offsetY > statusBarHeight {
            self.title = "App"
        } else {
            self.title = ""
        }
    }
}

extension HomeViewController {
    func loadLocalData () {
        let path = Bundle.main.path(forResource: "content", ofType: "json")
        let url = URL(fileURLWithPath: path!)
        do {
            let data = try Data(contentsOf: url)
            let jsonData: Any = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers)
            guard let dic = jsonData as? [String: Any] else { return }
            let model = BaseModel<HomeModel>.deserialize(from: dic)
            dataList = model?.dataList ?? []
        } catch let error as Error? {
            print("读取失败", error as Any)
        }
    }
    func netWorkRequest() {
        
    }
    
    func headerRefresh() {
        self.modelList.removeAll()
        currentPage = 1
        for i in 0..<pageSize {
            modelList.append(dataList[i])
        }
        if modelList.count > 0 {
            self.tableView.mj_footer = MJRefreshBackNormalFooter(refreshingBlock: { [weak self] in
                self?.footerRefresh()
            })
        } else {
            self.tableView.mj_footer = nil
        }
        Thread.sleep(forTimeInterval: 2)
        self.tableView.mj_header?.endRefreshing()
        self.tableView.reloadData()
    }
    
    func footerRefresh() {
        currentPage += 1
        for i in modelList.count..<pageSize+modelList.count {
            modelList.append(dataList[i])
        }
        Thread.sleep(forTimeInterval: 2)
        self.tableView.mj_footer?.endRefreshing()
        self.tableView.reloadData()
        if currentPage >= 5 {
            self.tableView.mj_footer?.endRefreshingWithNoMoreData()
        }
    }
}
