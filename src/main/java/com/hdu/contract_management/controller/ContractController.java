package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Review;
import com.hdu.contract_management.entity.ReviewProgress;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.RecordService;
import com.hdu.contract_management.service.ReviewProgressService;
import com.hdu.contract_management.service.ReviewService;
import com.hdu.contract_management.utils.ResultUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同表 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-03-22
 */
@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    RecordService recordService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewProgressService reviewProgressService;

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @RequestMapping("/uploadContract")
    public ResultUtil uploadContractfile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "contractId", required = false) Integer contractId) throws IOException {
        String path = "";
        String fileName = "";
        Contract contract = new Contract();
        if (contractId != null)
            contract = contractService.getById(contractId);
        if (file.isEmpty()) {
            System.out.println("文件为空");
            return ResultUtil.error("文件为空");
        } else {
            fileName = file.getOriginalFilename();
            contract.setFilePath(fileName);
            contractService.saveOrUpdate(contract);
            QueryWrapper<Contract> queryWrapper = new QueryWrapper<Contract>();
            queryWrapper.eq("file_path", fileName);
            queryWrapper.last("LIMIT 1");
            queryWrapper.orderByDesc("id");
            contract = contractService.getOne(queryWrapper);
            path = new File(UPLOAD_FOLDER + File.separator + contract.getId()).getAbsolutePath() + File.separator + fileName;
            File fileDir = new File(path);
            if (!fileDir.getParentFile().exists() && !fileDir.isDirectory()) {
                fileDir.getParentFile().mkdirs();
            }
            try {
                //以原来的名称命名,覆盖掉旧的
                System.out.println("上传的文件：" + fileName + "," + "，保存的路径为：" + fileDir);
                file.transferTo(fileDir);
                //或者下面的
                // Path path = Paths.get(storagePath);
                //Files.write(path,multipartFiles[i].getBytes());
            } catch (Exception e) {
                return ResultUtil.error(e.getMessage());
            }
        }

        return ResultUtil.success(fileName, contract);
    }

    @RequestMapping("/downloadContract")
    public void downloadContract(HttpServletRequest request, HttpServletResponse response) {
        String contractId = request.getParameter("id");
        String fileName = request.getParameter("filePath");
        String path = UPLOAD_FOLDER + File.separator + contractId + File.separator + fileName;
        File file = new File(path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            //判断文件父目录是否存在
            if (file.exists()) {
                //设置返回文件信息
                if (fileName.endsWith(".docx")) {
                    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8");
                } else if (fileName.endsWith(".doc")) {
                    response.setContentType("application/msword;charset=UTF-8");
                }
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @PostMapping("/delfile")
    public ResultUtil delContractFile(@RequestParam(value = "id") Integer id, @RequestParam(value = "filepath") String filepath) throws IOException {
        String path = UPLOAD_FOLDER + File.separator + id + File.separator + filepath;
        Contract contract = contractService.getById(id);
        System.out.println(path);
        if (contract.getName() != null) {
            UpdateWrapper<Contract> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id).set("file_path", null);
            contractService.update(wrapper);
        } else {
            contractService.removeById(id);
        }
        File file = new File(path);
        try {
            if (file.exists()) {
                file.delete();
                System.out.println(path + "文件已删除");
                return ResultUtil.success("已删除");
            } else
                return ResultUtil.error("文件不存在");
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResultUtil increaseContract(HttpServletRequest request) {
        Contract contract = new Contract();
        contract.setId(Integer.parseInt(request.getParameter("id")));
        contract.setName(request.getParameter("contract_name"));
        contract.setPartyB(request.getParameter("party_b"));
        contract.setFilePath(request.getParameter("filepath"));
        contract.setDepartment(Integer.parseInt(request.getParameter("department")));
        contract.setSignPeople(Integer.parseInt(request.getParameter("sign_people")));
        contract.setContractType(Integer.parseInt(request.getParameter("type")));
        contract.setTotal(Integer.parseInt(request.getParameter("total")));
        contract.setRemainder(Integer.parseInt(request.getParameter("total")));
        contract.setContractDescribe(request.getParameter("describe"));
        contract.setStartDate(LocalDate.parse(request.getParameter("start_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setStopDate(LocalDate.parse(request.getParameter("stop_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setContractState(1);
        System.out.println(contract.toString());
        if (contractService.saveOrUpdate(contract)) {
            reviewProgressService.increasePregressByContract(contract);
            return ResultUtil.success("新增合同成功");

        } else
            return ResultUtil.error("新增合同失败");
    }

    @GetMapping("/")
    public ResultUtil getAllContract(HttpServletRequest request) {
//        System.out.println(request.getParameter("sign_people"));
        Integer signPeople = Integer.parseInt(request.getParameter("sign_people"));
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_people", signPeople);
        queryWrapper.orderByDesc("id");
        List<Contract> list = contractService.list(queryWrapper);
        return ResultUtil.success("查询成功", list);
    }

    @GetMapping("/{id}")
    public ResultUtil getContractById(@PathVariable(value = "id") Integer id) {
        Contract contract = contractService.getById(id);
        if (contract != null) {
            return ResultUtil.success("查询成功", contract);
        } else
            return ResultUtil.error("查询失败");
    }

    @DeleteMapping("/{id}")
    public ResultUtil delContract(@PathVariable(value = "id") Integer id) {
        if (contractService.removeById(id))
            if (reviewService.remove(new QueryWrapper<Review>().eq("contract_id", id)))
                return ResultUtil.success("删除合同成功");
            else
                return ResultUtil.error("删除合同失败");
        else
            return ResultUtil.error("删除合同失败");

    }

    @GetMapping("/findall")
    public ResultUtil findAll(HttpServletRequest request) {
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        IPage<Contract> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Contract> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        IPage<Contract> page1 = contractService.page(page, wrapper);

        return ResultUtil.success("查询成功", page1);
    }

    @GetMapping("/review")
    public ResultUtil getReviewContract(HttpServletRequest request) {
        Integer signPeople = Integer.parseInt(request.getParameter("sign_people"));
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_people", signPeople);
        queryWrapper.eq("contract_state", 0);
        queryWrapper.or();
        queryWrapper.eq("sign_people", signPeople);
        queryWrapper.eq("contract_state", 1);
        queryWrapper.orderByDesc("id");
        List<Contract> list = contractService.list(queryWrapper);
        return ResultUtil.success("查询成功", list);
    }

    @GetMapping("/filed")
    public ResultUtil getFiledContract(@RequestParam(value = "sign_people", required = false) Integer signPeople) {
        if (signPeople != null) {
            QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sign_people", signPeople);
            queryWrapper.eq("contract_state", 3);
            queryWrapper.orderByDesc("id");
            List<Contract> list = contractService.list(queryWrapper);
            return ResultUtil.success("查询已归档成功", list);
        } else return ResultUtil.error("查询已归档失败");

    }

    @PutMapping("/")
    public ResultUtil modifyContract(HttpServletRequest request) {
        Contract contract = new Contract();
        contract.setId(Integer.parseInt(request.getParameter("id")));
        contract.setName(request.getParameter("contract_name"));
        contract.setPartyB(request.getParameter("party_b"));
        contract.setFilePath(request.getParameter("filepath"));
        contract.setDepartment(Integer.parseInt(request.getParameter("department")));
        contract.setSignPeople(Integer.parseInt(request.getParameter("sign_people")));
        contract.setContractType(Integer.parseInt(request.getParameter("type")));
        contract.setTotal(Integer.parseInt(request.getParameter("total")));
        contract.setRemainder(Integer.parseInt(request.getParameter("total")));
        contract.setContractDescribe(request.getParameter("describe"));
        contract.setStartDate(LocalDate.parse(request.getParameter("start_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setStopDate(LocalDate.parse(request.getParameter("stop_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setContractState(1);
        System.out.println(contract.toString());
        reviewProgressService.increasePregressByModifyContract(contract);
        if (contractService.saveOrUpdate(contract))
            return ResultUtil.success("修改合同成功");
        else
            return ResultUtil.error("修改合同失败");
    }

    @GetMapping("/execute")
    public ResultUtil getExecuteContract(HttpServletRequest request) {
        Integer signPeople = Integer.parseInt(request.getParameter("sign_people"));
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_people", signPeople);
        queryWrapper.eq("contract_state", 2);
        queryWrapper.orderByDesc("id");
        List<Contract> list = contractService.list(queryWrapper);
        return ResultUtil.success("查询成功", list);
    }

    @GetMapping("/waitreview")
    public ResultUtil getWaitReviewContract(HttpServletRequest request) {
        Integer department = Integer.parseInt(request.getParameter("department"));
        List<ReviewProgress> list = reviewProgressService.list(new QueryWrapper<ReviewProgress>().eq("next_department", department));
        if (list.size() == 0)
            return ResultUtil.success("查询成功，但无数据");
        List<Integer> idList = new ArrayList<>();
        for (ReviewProgress r : list) {
            idList.add(r.getContractId());
        }
        List<Contract> contracts = contractService.listByIds(idList);
        return ResultUtil.success("查询成功", contracts);
    }

    @PostMapping("/file")
    public ResultUtil filingContract(Contract contract) {
        UpdateWrapper<Contract> wrapper = new UpdateWrapper();
        wrapper.eq("id", contract.getId());
        wrapper.set("contract_state", 3);
        if (contractService.update(wrapper)) {
            return ResultUtil.success("归档成功");
        } else
            return ResultUtil.error("归档失败");

    }
//     获取当前审批中合同数、待审批合同以及执行中合同数

    /**
     *
     * @param userId 用户id
     * @return int approving 审批中合同数
     * @return int waitApprove 待审批
     * @return int execute 执行中
     */
    @GetMapping("/contractnumber")
    public ResultUtil contractNumber(Integer userId){
        QueryWrapper<Contract> wrapper = new QueryWrapper();
        wrapper.eq("sign_people", userId);
        wrapper.eq("contract_state", 0);
        wrapper.or();
        wrapper.eq("sign_people", userId);
        wrapper.eq("contract_state", 1);
        int approving = contractService.count(wrapper);
        wrapper.clear();
        int waitApprove = reviewProgressService.waitApproveCount(userId);
        wrapper.eq("sign_people", userId);
        wrapper.eq("contract_state", 2);
        int execute = contractService.count(wrapper);
        Map<String,Object> result = new HashMap<>();
        result.put("approving",approving);
        result.put("waitApprove",waitApprove);
        result.put("execute",execute);
        return ResultUtil.success("111",result);
    }

    @GetMapping("/saleData")
    public ResultUtil saleData(Integer userId){
        return ResultUtil.success("查询销售相关成功",contractService.getSaleDate(userId));
    }
    @GetMapping("/purchaseData")
    public ResultUtil purchaseData(Integer userId){
        return ResultUtil.success("查询销售相关成功",contractService.getPurchaseDate(userId));
    }
    @GetMapping("/expire")
    public ResultUtil expire(Integer userId){
        return ResultUtil.success("查询临期成功",contractService.expire(userId));
    }

 }

